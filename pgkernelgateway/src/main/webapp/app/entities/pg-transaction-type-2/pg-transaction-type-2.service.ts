import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgTransactionType2 } from 'app/shared/model/pg-transaction-type-2.model';

type EntityResponseType = HttpResponse<IPgTransactionType2>;
type EntityArrayResponseType = HttpResponse<IPgTransactionType2[]>;

@Injectable({ providedIn: 'root' })
export class PgTransactionType2Service {
  public resourceUrl = SERVER_API_URL + 'api/pg-transaction-type-2-s';

  constructor(protected http: HttpClient) {}

  create(pgTransactionType2: IPgTransactionType2): Observable<EntityResponseType> {
    return this.http.post<IPgTransactionType2>(this.resourceUrl, pgTransactionType2, { observe: 'response' });
  }

  update(pgTransactionType2: IPgTransactionType2): Observable<EntityResponseType> {
    return this.http.put<IPgTransactionType2>(this.resourceUrl, pgTransactionType2, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgTransactionType2>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgTransactionType2[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
