import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDetailTransaction } from 'app/shared/model/detail-transaction.model';

type EntityResponseType = HttpResponse<IDetailTransaction>;
type EntityArrayResponseType = HttpResponse<IDetailTransaction[]>;

@Injectable({ providedIn: 'root' })
export class DetailTransactionService {
  public resourceUrl = SERVER_API_URL + 'api/detail-transactions';

  constructor(protected http: HttpClient) {}

  create(detailTransaction: IDetailTransaction): Observable<EntityResponseType> {
    return this.http.post<IDetailTransaction>(this.resourceUrl, detailTransaction, { observe: 'response' });
  }

  update(detailTransaction: IDetailTransaction): Observable<EntityResponseType> {
    return this.http.put<IDetailTransaction>(this.resourceUrl, detailTransaction, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDetailTransaction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDetailTransaction[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
