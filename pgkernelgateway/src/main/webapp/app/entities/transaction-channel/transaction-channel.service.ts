import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransactionChannel } from 'app/shared/model/transaction-channel.model';

type EntityResponseType = HttpResponse<ITransactionChannel>;
type EntityArrayResponseType = HttpResponse<ITransactionChannel[]>;

@Injectable({ providedIn: 'root' })
export class TransactionChannelService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-channels';

  constructor(protected http: HttpClient) {}

  create(transactionChannel: ITransactionChannel): Observable<EntityResponseType> {
    return this.http.post<ITransactionChannel>(this.resourceUrl, transactionChannel, { observe: 'response' });
  }

  update(transactionChannel: ITransactionChannel): Observable<EntityResponseType> {
    return this.http.put<ITransactionChannel>(this.resourceUrl, transactionChannel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransactionChannel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionChannel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
