import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccountStatus } from 'app/shared/model/account-status.model';

type EntityResponseType = HttpResponse<IAccountStatus>;
type EntityArrayResponseType = HttpResponse<IAccountStatus[]>;

@Injectable({ providedIn: 'root' })
export class AccountStatusService {
  public resourceUrl = SERVER_API_URL + 'api/account-statuses';

  constructor(protected http: HttpClient) {}

  create(accountStatus: IAccountStatus): Observable<EntityResponseType> {
    return this.http.post<IAccountStatus>(this.resourceUrl, accountStatus, { observe: 'response' });
  }

  update(accountStatus: IAccountStatus): Observable<EntityResponseType> {
    return this.http.put<IAccountStatus>(this.resourceUrl, accountStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccountStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccountStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
