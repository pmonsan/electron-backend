import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILimitType } from 'app/shared/model/limit-type.model';

type EntityResponseType = HttpResponse<ILimitType>;
type EntityArrayResponseType = HttpResponse<ILimitType[]>;

@Injectable({ providedIn: 'root' })
export class LimitTypeService {
  public resourceUrl = SERVER_API_URL + 'api/limit-types';

  constructor(protected http: HttpClient) {}

  create(limitType: ILimitType): Observable<EntityResponseType> {
    return this.http.post<ILimitType>(this.resourceUrl, limitType, { observe: 'response' });
  }

  update(limitType: ILimitType): Observable<EntityResponseType> {
    return this.http.put<ILimitType>(this.resourceUrl, limitType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILimitType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILimitType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
