import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgResponseCode } from 'app/shared/model/pg-response-code.model';

type EntityResponseType = HttpResponse<IPgResponseCode>;
type EntityArrayResponseType = HttpResponse<IPgResponseCode[]>;

@Injectable({ providedIn: 'root' })
export class PgResponseCodeService {
  public resourceUrl = SERVER_API_URL + 'api/pg-response-codes';

  constructor(protected http: HttpClient) {}

  create(pgResponseCode: IPgResponseCode): Observable<EntityResponseType> {
    return this.http.post<IPgResponseCode>(this.resourceUrl, pgResponseCode, { observe: 'response' });
  }

  update(pgResponseCode: IPgResponseCode): Observable<EntityResponseType> {
    return this.http.put<IPgResponseCode>(this.resourceUrl, pgResponseCode, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgResponseCode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgResponseCode[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
