import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceAuthentication } from 'app/shared/model/service-authentication.model';

type EntityResponseType = HttpResponse<IServiceAuthentication>;
type EntityArrayResponseType = HttpResponse<IServiceAuthentication[]>;

@Injectable({ providedIn: 'root' })
export class ServiceAuthenticationService {
  public resourceUrl = SERVER_API_URL + 'api/service-authentications';

  constructor(protected http: HttpClient) {}

  create(serviceAuthentication: IServiceAuthentication): Observable<EntityResponseType> {
    return this.http.post<IServiceAuthentication>(this.resourceUrl, serviceAuthentication, { observe: 'response' });
  }

  update(serviceAuthentication: IServiceAuthentication): Observable<EntityResponseType> {
    return this.http.put<IServiceAuthentication>(this.resourceUrl, serviceAuthentication, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServiceAuthentication>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceAuthentication[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
