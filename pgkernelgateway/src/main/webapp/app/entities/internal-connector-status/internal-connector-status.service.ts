import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInternalConnectorStatus } from 'app/shared/model/internal-connector-status.model';

type EntityResponseType = HttpResponse<IInternalConnectorStatus>;
type EntityArrayResponseType = HttpResponse<IInternalConnectorStatus[]>;

@Injectable({ providedIn: 'root' })
export class InternalConnectorStatusService {
  public resourceUrl = SERVER_API_URL + 'api/internal-connector-statuses';

  constructor(protected http: HttpClient) {}

  create(internalConnectorStatus: IInternalConnectorStatus): Observable<EntityResponseType> {
    return this.http.post<IInternalConnectorStatus>(this.resourceUrl, internalConnectorStatus, { observe: 'response' });
  }

  update(internalConnectorStatus: IInternalConnectorStatus): Observable<EntityResponseType> {
    return this.http.put<IInternalConnectorStatus>(this.resourceUrl, internalConnectorStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInternalConnectorStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInternalConnectorStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
