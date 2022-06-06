#!/usr/bin/env python3

import psycopg2
import logging
import Cve
from config import config


def connect_to_postgres():
    logging.info("Connecting to PostgreSQL DB")
    conn = None

    try:
        params = config()
        conn = psycopg2.connect(**params)

        cur = conn.cursor()
        cur.execute('SELECT version()')
        db_version = cur.fetchone()
        print(db_version)
        cur.close()

    except (Exception, psycopg2.DatabaseError) as error:
        print(error)

    return conn


def close_connection(conn):
    if conn is not None:
        conn.close()


def execute_query(conn, query, no_res=False, args=()):
    cur = None
    results = None
    try:
        cur = conn.cursor()
        results = cur.execute(query, args)
        if not no_res:
            results = cur.fetchall()
        else:
            conn.commit()
    except Exception as e:
        logging.error("DB query {} failed with error {}".format(query, str(e)))
    finally:
        if cur is not None:
            cur.close()

    return results


if __name__ == '__main__':
    con = connect_to_postgres()
    vulns = execute_query(con, "SELECT * FROM vulnerabilities")
    if vulns is not None:
        for vuln in vulns:
            print(vuln)
    close_connection(con)
