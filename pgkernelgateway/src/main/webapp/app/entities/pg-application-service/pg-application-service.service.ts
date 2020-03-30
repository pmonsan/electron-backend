import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgApplicationService } from 'app/shared/model/pg-application-service.model';

type EntityResponseType = HttpResponse<IPgApplicationService>;
type EntityArrayResponseType = HttpResponse<IPgApplicationService[]>;

@Injectable({ providedIn: 'root' })
export class PgApplicationServiceService {
  public resourceUrl = SERVER_API_URL + 'api/pg-application-services';

  constructor(protected http: HttpClient) {}

  create(pgApplicationService: IPgApplicationService): Observable<EntityResponseType> {
    return this.http.post<IPgApplicationService>(this.resourceUrl, pgApplicationService, { observe: 'response' });
  }

  update(pgApplicationService: IPgApplicationService): Observable<EntityResponseType> {
    return this.http.put<IPgApplicationService>(this.resourceUrl, pgApplicationService, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgApplicationService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgApplicationService[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
