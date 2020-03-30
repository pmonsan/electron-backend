import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransactionInfo } from 'app/shared/model/transaction-info.model';

type EntityResponseType = HttpResponse<ITransactionInfo>;
type EntityArrayResponseType = HttpResponse<ITransactionInfo[]>;

@Injectable({ providedIn: 'root' })
export class TransactionInfoService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-infos';

  constructor(protected http: HttpClient) {}

  create(transactionInfo: ITransactionInfo): Observable<EntityResponseType> {
    return this.http.post<ITransactionInfo>(this.resourceUrl, transactionInfo, { observe: 'response' });
  }

  update(transactionInfo: ITransactionInfo): Observable<EntityResponseType> {
    return this.http.put<ITransactionInfo>(this.resourceUrl, transactionInfo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransactionInfo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionInfo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
