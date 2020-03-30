import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgTransactionType1 } from 'app/shared/model/pg-transaction-type-1.model';

type EntityResponseType = HttpResponse<IPgTransactionType1>;
type EntityArrayResponseType = HttpResponse<IPgTransactionType1[]>;

@Injectable({ providedIn: 'root' })
export class PgTransactionType1Service {
  public resourceUrl = SERVER_API_URL + 'api/pg-transaction-type-1-s';

  constructor(protected http: HttpClient) {}

  create(pgTransactionType1: IPgTransactionType1): Observable<EntityResponseType> {
    return this.http.post<IPgTransactionType1>(this.resourceUrl, pgTransactionType1, { observe: 'response' });
  }

  update(pgTransactionType1: IPgTransactionType1): Observable<EntityResponseType> {
    return this.http.put<IPgTransactionType1>(this.resourceUrl, pgTransactionType1, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgTransactionType1>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgTransactionType1[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
