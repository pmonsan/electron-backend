import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOperationType } from 'app/shared/model/operation-type.model';

type EntityResponseType = HttpResponse<IOperationType>;
type EntityArrayResponseType = HttpResponse<IOperationType[]>;

@Injectable({ providedIn: 'root' })
export class OperationTypeService {
  public resourceUrl = SERVER_API_URL + 'api/operation-types';

  constructor(protected http: HttpClient) {}

  create(operationType: IOperationType): Observable<EntityResponseType> {
    return this.http.post<IOperationType>(this.resourceUrl, operationType, { observe: 'response' });
  }

  update(operationType: IOperationType): Observable<EntityResponseType> {
    return this.http.put<IOperationType>(this.resourceUrl, operationType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOperationType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOperationType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
