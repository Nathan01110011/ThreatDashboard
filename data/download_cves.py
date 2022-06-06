#!/usr/bin/env python3

import os

import requests
import zipfile


def download_file(url, file_path):
    r = requests.get(url, allow_redirects=True, stream=True)
    if r.ok:
        print(r.headers.get('content-type'))
        print("saving to", os.path.abspath(file_path))
        with open(file_path, 'wb') as f:
            for chunk in r.iter_content(chunk_size=1024 * 8):
                if chunk:
                    print("...")
                    f.write(chunk)
                    f.flush()
            f.close()
    else:  # HTTP status code 4XX/5XX
        print("Download failed: status code {}\n{}".format(r.status_code, r.text))


if __name__ == '__main__':
    url = "https://nvd.nist.gov/feeds/json/cve/1.1/nvdcve-1.1-recent.json.zip"
    file_name = url.split('/')[-1]

    print("Download CVEs from " + url)
    download_file(url, file_name)

    target_dir = "."
    print("Extract CVE as JSON from " + file_name)
    zipfile.ZipFile(file_name).extractall(target_dir)
