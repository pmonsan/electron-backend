import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgMessageStatus } from 'app/shared/model/pg-message-status.model';

type EntityResponseType = HttpResponse<IPgMessageStatus>;
type EntityArrayResponseType = HttpResponse<IPgMessageStatus[]>;

@Injectable({ providedIn: 'root' })
export class PgMessageStatusService {
  public resourceUrl = SERVER_API_URL + 'api/pg-message-statuses';

  constructor(protected http: HttpClient) {}

  create(pgMessageStatus: IPgMessageStatus): Observable<EntityResponseType> {
    return this.http.post<IPgMessageStatus>(this.resourceUrl, pgMessageStatus, { observe: 'response' });
  }

  update(pgMessageStatus: IPgMessageStatus): Observable<EntityResponseType> {
    return this.http.put<IPgMessageStatus>(this.resourceUrl, pgMessageStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgMessageStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgMessageStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
