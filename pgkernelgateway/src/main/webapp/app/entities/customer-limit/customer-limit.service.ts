import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICustomerLimit } from 'app/shared/model/customer-limit.model';

type EntityResponseType = HttpResponse<ICustomerLimit>;
type EntityArrayResponseType = HttpResponse<ICustomerLimit[]>;

@Injectable({ providedIn: 'root' })
export class CustomerLimitService {
  public resourceUrl = SERVER_API_URL + 'api/customer-limits';

  constructor(protected http: HttpClient) {}

  create(customerLimit: ICustomerLimit): Observable<EntityResponseType> {
    return this.http.post<ICustomerLimit>(this.resourceUrl, customerLimit, { observe: 'response' });
  }

  update(customerLimit: ICustomerLimit): Observable<EntityResponseType> {
    return this.http.put<ICustomerLimit>(this.resourceUrl, customerLimit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomerLimit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerLimit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
