import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICustomerType } from 'app/shared/model/customer-type.model';

type EntityResponseType = HttpResponse<ICustomerType>;
type EntityArrayResponseType = HttpResponse<ICustomerType[]>;

@Injectable({ providedIn: 'root' })
export class CustomerTypeService {
  public resourceUrl = SERVER_API_URL + 'api/customer-types';

  constructor(protected http: HttpClient) {}

  create(customerType: ICustomerType): Observable<EntityResponseType> {
    return this.http.post<ICustomerType>(this.resourceUrl, customerType, { observe: 'response' });
  }

  update(customerType: ICustomerType): Observable<EntityResponseType> {
    return this.http.put<ICustomerType>(this.resourceUrl, customerType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustomerType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustomerType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
