import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServiceLimit } from 'app/shared/model/service-limit.model';

type EntityResponseType = HttpResponse<IServiceLimit>;
type EntityArrayResponseType = HttpResponse<IServiceLimit[]>;

@Injectable({ providedIn: 'root' })
export class ServiceLimitService {
  public resourceUrl = SERVER_API_URL + 'api/service-limits';

  constructor(protected http: HttpClient) {}

  create(serviceLimit: IServiceLimit): Observable<EntityResponseType> {
    return this.http.post<IServiceLimit>(this.resourceUrl, serviceLimit, { observe: 'response' });
  }

  update(serviceLimit: IServiceLimit): Observable<EntityResponseType> {
    return this.http.put<IServiceLimit>(this.resourceUrl, serviceLimit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServiceLimit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceLimit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
