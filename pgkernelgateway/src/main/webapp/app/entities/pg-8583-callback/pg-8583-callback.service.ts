import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPg8583Callback } from 'app/shared/model/pg-8583-callback.model';

type EntityResponseType = HttpResponse<IPg8583Callback>;
type EntityArrayResponseType = HttpResponse<IPg8583Callback[]>;

@Injectable({ providedIn: 'root' })
export class Pg8583CallbackService {
  public resourceUrl = SERVER_API_URL + 'api/pg-8583-callbacks';

  constructor(protected http: HttpClient) {}

  create(pg8583Callback: IPg8583Callback): Observable<EntityResponseType> {
    return this.http.post<IPg8583Callback>(this.resourceUrl, pg8583Callback, { observe: 'response' });
  }

  update(pg8583Callback: IPg8583Callback): Observable<EntityResponseType> {
    return this.http.put<IPg8583Callback>(this.resourceUrl, pg8583Callback, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPg8583Callback>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPg8583Callback[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
