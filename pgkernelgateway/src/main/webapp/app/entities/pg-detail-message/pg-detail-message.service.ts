import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgDetailMessage } from 'app/shared/model/pg-detail-message.model';

type EntityResponseType = HttpResponse<IPgDetailMessage>;
type EntityArrayResponseType = HttpResponse<IPgDetailMessage[]>;

@Injectable({ providedIn: 'root' })
export class PgDetailMessageService {
  public resourceUrl = SERVER_API_URL + 'api/pg-detail-messages';

  constructor(protected http: HttpClient) {}

  create(pgDetailMessage: IPgDetailMessage): Observable<EntityResponseType> {
    return this.http.post<IPgDetailMessage>(this.resourceUrl, pgDetailMessage, { observe: 'response' });
  }

  update(pgDetailMessage: IPgDetailMessage): Observable<EntityResponseType> {
    return this.http.put<IPgDetailMessage>(this.resourceUrl, pgDetailMessage, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgDetailMessage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgDetailMessage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
