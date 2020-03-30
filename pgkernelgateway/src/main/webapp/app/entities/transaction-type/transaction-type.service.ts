import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransactionType } from 'app/shared/model/transaction-type.model';

type EntityResponseType = HttpResponse<ITransactionType>;
type EntityArrayResponseType = HttpResponse<ITransactionType[]>;

@Injectable({ providedIn: 'root' })
export class TransactionTypeService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-types';

  constructor(protected http: HttpClient) {}

  create(transactionType: ITransactionType): Observable<EntityResponseType> {
    return this.http.post<ITransactionType>(this.resourceUrl, transactionType, { observe: 'response' });
  }

  update(transactionType: ITransactionType): Observable<EntityResponseType> {
    return this.http.put<ITransactionType>(this.resourceUrl, transactionType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransactionType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
