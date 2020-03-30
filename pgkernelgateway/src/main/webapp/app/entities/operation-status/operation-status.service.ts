import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOperationStatus } from 'app/shared/model/operation-status.model';

type EntityResponseType = HttpResponse<IOperationStatus>;
type EntityArrayResponseType = HttpResponse<IOperationStatus[]>;

@Injectable({ providedIn: 'root' })
export class OperationStatusService {
  public resourceUrl = SERVER_API_URL + 'api/operation-statuses';

  constructor(protected http: HttpClient) {}

  create(operationStatus: IOperationStatus): Observable<EntityResponseType> {
    return this.http.post<IOperationStatus>(this.resourceUrl, operationStatus, { observe: 'response' });
  }

  update(operationStatus: IOperationStatus): Observable<EntityResponseType> {
    return this.http.put<IOperationStatus>(this.resourceUrl, operationStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOperationStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOperationStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
