import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransactionGroup } from 'app/shared/model/transaction-group.model';

type EntityResponseType = HttpResponse<ITransactionGroup>;
type EntityArrayResponseType = HttpResponse<ITransactionGroup[]>;

@Injectable({ providedIn: 'root' })
export class TransactionGroupService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-groups';

  constructor(protected http: HttpClient) {}

  create(transactionGroup: ITransactionGroup): Observable<EntityResponseType> {
    return this.http.post<ITransactionGroup>(this.resourceUrl, transactionGroup, { observe: 'response' });
  }

  update(transactionGroup: ITransactionGroup): Observable<EntityResponseType> {
    return this.http.put<ITransactionGroup>(this.resourceUrl, transactionGroup, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransactionGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionGroup[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
