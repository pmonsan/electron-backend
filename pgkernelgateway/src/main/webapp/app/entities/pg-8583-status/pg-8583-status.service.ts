import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPg8583Status } from 'app/shared/model/pg-8583-status.model';

type EntityResponseType = HttpResponse<IPg8583Status>;
type EntityArrayResponseType = HttpResponse<IPg8583Status[]>;

@Injectable({ providedIn: 'root' })
export class Pg8583StatusService {
  public resourceUrl = SERVER_API_URL + 'api/pg-8583-statuses';

  constructor(protected http: HttpClient) {}

  create(pg8583Status: IPg8583Status): Observable<EntityResponseType> {
    return this.http.post<IPg8583Status>(this.resourceUrl, pg8583Status, { observe: 'response' });
  }

  update(pg8583Status: IPg8583Status): Observable<EntityResponseType> {
    return this.http.put<IPg8583Status>(this.resourceUrl, pg8583Status, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPg8583Status>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPg8583Status[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
