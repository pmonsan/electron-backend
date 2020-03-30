import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgService } from 'app/shared/model/pg-service.model';

type EntityResponseType = HttpResponse<IPgService>;
type EntityArrayResponseType = HttpResponse<IPgService[]>;

@Injectable({ providedIn: 'root' })
export class PgServiceService {
  public resourceUrl = SERVER_API_URL + 'api/pg-services';

  constructor(protected http: HttpClient) {}

  create(pgService: IPgService): Observable<EntityResponseType> {
    return this.http.post<IPgService>(this.resourceUrl, pgService, { observe: 'response' });
  }

  update(pgService: IPgService): Observable<EntityResponseType> {
    return this.http.put<IPgService>(this.resourceUrl, pgService, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgService>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgService[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
