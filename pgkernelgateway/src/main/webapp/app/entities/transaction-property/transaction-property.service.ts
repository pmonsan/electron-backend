import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITransactionProperty } from 'app/shared/model/transaction-property.model';

type EntityResponseType = HttpResponse<ITransactionProperty>;
type EntityArrayResponseType = HttpResponse<ITransactionProperty[]>;

@Injectable({ providedIn: 'root' })
export class TransactionPropertyService {
  public resourceUrl = SERVER_API_URL + 'api/transaction-properties';

  constructor(protected http: HttpClient) {}

  create(transactionProperty: ITransactionProperty): Observable<EntityResponseType> {
    return this.http.post<ITransactionProperty>(this.resourceUrl, transactionProperty, { observe: 'response' });
  }

  update(transactionProperty: ITransactionProperty): Observable<EntityResponseType> {
    return this.http.put<ITransactionProperty>(this.resourceUrl, transactionProperty, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITransactionProperty>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITransactionProperty[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
