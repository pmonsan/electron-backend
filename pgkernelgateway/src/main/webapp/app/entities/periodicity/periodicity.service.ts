import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPeriodicity } from 'app/shared/model/periodicity.model';

type EntityResponseType = HttpResponse<IPeriodicity>;
type EntityArrayResponseType = HttpResponse<IPeriodicity[]>;

@Injectable({ providedIn: 'root' })
export class PeriodicityService {
  public resourceUrl = SERVER_API_URL + 'api/periodicities';

  constructor(protected http: HttpClient) {}

  create(periodicity: IPeriodicity): Observable<EntityResponseType> {
    return this.http.post<IPeriodicity>(this.resourceUrl, periodicity, { observe: 'response' });
  }

  update(periodicity: IPeriodicity): Observable<EntityResponseType> {
    return this.http.put<IPeriodicity>(this.resourceUrl, periodicity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPeriodicity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPeriodicity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
