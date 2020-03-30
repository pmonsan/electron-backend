import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransactionStatus } from 'app/shared/model/transaction-status.model';

type EntityResponseType = HttpResponse<ITransactionStatus>;
type EntityArrayResponseType = HttpResponse<ITransactionStatus[]>;

@Injectable({ providedIn: 'root' })
export class TransactionStatusService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-statuses';

  constructor(protected http: HttpClient) {}

  create(transactionStatus: ITransactionStatus): Observable<EntityResponseType> {
    return this.http.post<ITransactionStatus>(this.resourceUrl, transactionStatus, { observe: 'response' });
  }

  update(transactionStatus: ITransactionStatus): Observable<EntityResponseType> {
    return this.http.put<ITransactionStatus>(this.resourceUrl, transactionStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransactionStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
