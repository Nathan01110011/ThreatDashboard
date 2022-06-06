import { Component, OnInit } from '@angular/core';
import { ApiFacadeService } from '../api-facade.service';
import { CveHighscoreService } from '../cve-highscore.service';

export interface CVEData {
  position: number;
  cve: string;
  severity: number;
  trend: number;
  mentions: number;
}

export interface POCData {
  position: number;
  name: string;
  repo: string;
  stars: number;
}

// TODO Remove
const CVE_DATA: CVEData[] = [
  { position: 1, cve: 'CVE-2021-38192', mentions: 123, severity: 5.0, trend: 1.0 },
  { position: 2, cve: 'CVE-2021-38193', mentions: 88, severity: 5.0, trend: 1.0 },
  { position: 3, cve: 'CVE-2021-38194', mentions: 76, severity: 5.0, trend: 1.0 },
  { position: 4, cve: 'CVE-2021-38195', mentions: 66, severity: 5.0, trend: 1.0 },
  { position: 5, cve: 'CVE-2021-38196', mentions: 50, severity: 5.0, trend: 1.0 },
  { position: 6, cve: 'CVE-2021-38197', mentions: 41, severity: 5.0, trend: 1.0 },
  { position: 7, cve: 'CVE-2021-38198', mentions: 38, severity: 5.0, trend: 1.0 },
  { position: 8, cve: 'CVE-2021-38199', mentions: 23, severity: 5.0, trend: 1.0 },
  { position: 9, cve: 'CVE-2021-38200', mentions: 22, severity: 5.0, trend: 1.0 },
  { position: 10, cve: 'CVE-2021-38201', mentions: 10, severity: 5.0, trend: 1.0 },
];

const POC_DATA: POCData[] = [
  { position: 1, name: 'CVE-2021-38192', repo: "https://giithub.com/something/something", stars: 50 },
  { position: 2, name: 'CVE-2021-38193', repo: "https://giithub.com/something/something", stars: 50 },
  { position: 3, name: 'CVE-2021-38194', repo: "https://giithub.com/something/something", stars: 50 },
  { position: 4, name: 'CVE-2021-38195', repo: "https://giithub.com/something/something", stars: 50 },
  { position: 5, name: 'CVE-2021-38196', repo: "https://giithub.com/something/something", stars: 50 },
];

const stringHash = (s: string) => {
  var hash = 0;
  if (s.length == 0) {
    return hash;
  }
  for (var i = 0; i < s.length; i++) {
    var char = s.charCodeAt(i);
    hash = ((hash << 5) - hash) + char;
    hash = hash & hash; // Convert to 32bit integer
  }
  return hash;
};

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  cveDisplayedColumns: string[] = ['position', 'cve', 'severity', 'trend', 'mentions', 'search'];
  cves = CVE_DATA;

  pocDisplayedColumns: string[] = ['position', 'name', 'repo', 'stars',];
  pocs = POC_DATA;

  constructor(private cveHighscore: CveHighscoreService, private api: ApiFacadeService) {

    cveHighscore.cveHighscores().subscribe((cveHighscores) => {
      this.cves = cveHighscores.map((cveHighscore, i) => {
        let hash = Math.abs(stringHash(cveHighscore.identifier));
        const cve: CVEData = {
          position: i + 1,
          cve: cveHighscore.identifier,
          mentions: cveHighscore.tweetCount,
          severity: (hash % 100) / 10,
          trend: (hash % 3) - 1
        };
        return cve;
      }).slice(0, 20);
    });

    api.latestPocs().subscribe((lpocs) => {
      lpocs.sort(function (a, b) {
        if (a.stars < b.stars) return 1;
        if (a.stars > b.stars) return -1;
        return 0;
      });

      this.pocs = lpocs.map((lpoc, i) => {
        const poc: POCData = {
          position: i + 1,
          name: lpoc.repoName,
          repo: lpoc.url,
          stars: lpoc.stars

        };
        return poc;
      }).slice(0, 20);;

    });
  }

  ngOnInit(): void {
    this.cveHighscore.refresh();
  }

  severityColor(severity: number): string {
    // console.log("severityColor(" + severity + ")");
    if (severity > 8) {
      return "warn";
    }
    else if (severity > 5) {
      return "accent";
    }
    return "primary";
  }

}
