import sys
import psycopg2
import logging


def connect_to_my_sql():
    logging.info("Connecting to DB")
    return psycopg2.connect(database="threat_dashboard", user="admin", password="password", host="127.0.0.1", port="5432")


def execute_query(query, cur, con, no_res=False, args=()):
    try:
        results = cur.execute(query, args)
        if not no_res:
            results = cur.fetchall()
        con.commit()
    except Exception as e:
        logging.error("DB query {} failed with error {}".format(query, str(e)))
        con.close()
        sys.exit(-1)
    return results
