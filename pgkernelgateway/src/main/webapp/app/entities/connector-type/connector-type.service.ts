import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConnectorType } from 'app/shared/model/connector-type.model';

type EntityResponseType = HttpResponse<IConnectorType>;
type EntityArrayResponseType = HttpResponse<IConnectorType[]>;

@Injectable({ providedIn: 'root' })
export class ConnectorTypeService {
  public resourceUrl = SERVER_API_URL + 'api/connector-types';

  constructor(protected http: HttpClient) {}

  create(connectorType: IConnectorType): Observable<EntityResponseType> {
    return this.http.post<IConnectorType>(this.resourceUrl, connectorType, { observe: 'response' });
  }

  update(connectorType: IConnectorType): Observable<EntityResponseType> {
    return this.http.put<IConnectorType>(this.resourceUrl, connectorType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConnectorType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConnectorType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
