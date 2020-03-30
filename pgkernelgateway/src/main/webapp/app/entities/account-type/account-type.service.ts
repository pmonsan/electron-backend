import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccountType } from 'app/shared/model/account-type.model';

type EntityResponseType = HttpResponse<IAccountType>;
type EntityArrayResponseType = HttpResponse<IAccountType[]>;

@Injectable({ providedIn: 'root' })
export class AccountTypeService {
  public resourceUrl = SERVER_API_URL + 'api/account-types';

  constructor(protected http: HttpClient) {}

  create(accountType: IAccountType): Observable<EntityResponseType> {
    return this.http.post<IAccountType>(this.resourceUrl, accountType, { observe: 'response' });
  }

  update(accountType: IAccountType): Observable<EntityResponseType> {
    return this.http.put<IAccountType>(this.resourceUrl, accountType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccountType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccountType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
