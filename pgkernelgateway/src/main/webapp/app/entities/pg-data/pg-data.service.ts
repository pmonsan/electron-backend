import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgData } from 'app/shared/model/pg-data.model';

type EntityResponseType = HttpResponse<IPgData>;
type EntityArrayResponseType = HttpResponse<IPgData[]>;

@Injectable({ providedIn: 'root' })
export class PgDataService {
  public resourceUrl = SERVER_API_URL + 'api/pg-data';

  constructor(protected http: HttpClient) {}

  create(pgData: IPgData): Observable<EntityResponseType> {
    return this.http.post<IPgData>(this.resourceUrl, pgData, { observe: 'response' });
  }

  update(pgData: IPgData): Observable<EntityResponseType> {
    return this.http.put<IPgData>(this.resourceUrl, pgData, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
