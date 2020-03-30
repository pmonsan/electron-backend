import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IConnector } from 'app/shared/model/connector.model';

type EntityResponseType = HttpResponse<IConnector>;
type EntityArrayResponseType = HttpResponse<IConnector[]>;

@Injectable({ providedIn: 'root' })
export class ConnectorService {
  public resourceUrl = SERVER_API_URL + 'api/connectors';

  constructor(protected http: HttpClient) {}

  create(connector: IConnector): Observable<EntityResponseType> {
    return this.http.post<IConnector>(this.resourceUrl, connector, { observe: 'response' });
  }

  update(connector: IConnector): Observable<EntityResponseType> {
    return this.http.put<IConnector>(this.resourceUrl, connector, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConnector>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConnector[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
