import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CveMention, CvePoc, Vulnerability } from './api-model';
import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root'
})
export class ApiFacadeService {

  constructor(private http: HttpClient) { }

  latestCvesMentions(): Observable<CveMention[]> {
    return this.http.get<CveMention[]>(`http://${environment.endpoint}/cves/latest?hoursFromNow=388`);
  }

  latestPocs(): Observable<CvePoc[]> {
    return this.http.get<CvePoc[]>(`http://${environment.endpoint}/github/latest?hoursFromNow=388`);
  }

  getVulnerability(id: string): Observable<Vulnerability> {
    return this.http.get<Vulnerability>(`http://${environment.endpoint}/vulnerabilities/get`, { params: new HttpParams().set("id", id)});
  }

}
