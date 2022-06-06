#!/usr/bin/env python3

import json
import requests
import logging
import db
from Cve import Cve


def parse_cve_json(file_name):
    cve_array = []

    f = open(file_name, )
    data = json.load(f)
    cve_items = data['CVE_Items']

    for cve_item in cve_items:
        cve = cve_item['cve']
        cve_id = cve
        #    print( "type=" + cve[ 'data_type' ] )
        meta = cve['CVE_data_meta']
        cve_id = meta['ID']

        desc = cve['description']
        desc_data = desc['description_data']
        desc_data_0 = desc_data[0]
        desc_value = desc_data_0['value']

        cwe = ""
        ptype = cve['problemtype']
        ptype_data = ptype['problemtype_data']
        for ptype_data_item in ptype_data:
            for desc_item in ptype_data_item['description']:
                cwe_value = desc_item['value']
                cwe = cwe + "," + cwe_value

        impact = cve_item['impact']

        cvss_base_score = 0.0
        cvss_severity = "unknown"
        if 'baseMetricV3' in impact:
            metric = impact['baseMetricV3']
            cvss = metric['cvssV3']
            cvss_base_score = cvss['baseScore']
            cvss_severity = cvss['baseSeverity']
        elif 'baseMetricV2' in impact:
            metric = impact['baseMetricV2']
            cvss = metric['cvssV2']
            cvss_base_score = cvss['baseScore']
            cvss_severity = metric['severity']

        published_date = cve_item['publishedDate']

        cve_instance = Cve(cve_id, desc_value, published_date, cvss_base_score, cvss_severity, cwe)
        cve_array.append(cve_instance)
    f.close()

    return cve_array


def download_cves(url, filename):
    r = requests.get(url, allow_redirects=True)
    print(r.headers.get('content-type'))
    f = open(filename, 'wb')
    f.write(r.content)
    f.close()


def query_insert_new_cve(cve):
    length = 1000
    desc = cve.desc.replace("'", "''")
    description = (desc[:length]) if len(desc) > length else desc

    query = "INSERT INTO vulnerabilities (vulnerability_id, vulnerability_title, vulnerability_cwe, vulnerability_date_published, cvss_base_score)" \
            f" VALUES ('{cve.cve_id}','{description}','{cve.cwe}','{cve.date_published}',{cve.score});"
    return query


if __name__ == '__main__':
    file_name = "nvdcve-1.1-recent.json"
    cves = parse_cve_json(file_name)

    con = None
    try:
        con = db.connect_to_postgres()
        for cve in cves:
            query = query_insert_new_cve(cve)
            db.execute_query(con, query, True)
    except Exception as e:
        logging.error("DB update failed with error {}".format(str(e)))
    finally:
        if con is not None:
            con.close()
