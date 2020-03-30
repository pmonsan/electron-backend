import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgChannel } from 'app/shared/model/pg-channel.model';

type EntityResponseType = HttpResponse<IPgChannel>;
type EntityArrayResponseType = HttpResponse<IPgChannel[]>;

@Injectable({ providedIn: 'root' })
export class PgChannelService {
  public resourceUrl = SERVER_API_URL + 'api/pg-channels';

  constructor(protected http: HttpClient) {}

  create(pgChannel: IPgChannel): Observable<EntityResponseType> {
    return this.http.post<IPgChannel>(this.resourceUrl, pgChannel, { observe: 'response' });
  }

  update(pgChannel: IPgChannel): Observable<EntityResponseType> {
    return this.http.put<IPgChannel>(this.resourceUrl, pgChannel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgChannel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgChannel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
