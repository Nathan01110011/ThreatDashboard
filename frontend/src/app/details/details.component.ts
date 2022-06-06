import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiFacadeService } from '../api-facade.service';
import { Vulnerability } from '../api-model';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent implements OnInit {

  public vulnerability: Vulnerability | null;

  constructor(private api: ApiFacadeService, private _route: ActivatedRoute) {
    this.vulnerability = null;
  }

  load(): void {
    const id = this._route.snapshot.paramMap.get('id');
    this.api.getVulnerability(String(id)).subscribe((data) => {
      this.vulnerability = data;
    });
    // this.vulnerability = this.VULNERABILITY_DATA;
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
  
  ngOnInit(): void {
    this.load();
  }
}
