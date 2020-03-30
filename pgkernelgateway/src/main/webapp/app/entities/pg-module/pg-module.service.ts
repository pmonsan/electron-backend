import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgModule } from 'app/shared/model/pg-module.model';

type EntityResponseType = HttpResponse<IPgModule>;
type EntityArrayResponseType = HttpResponse<IPgModule[]>;

@Injectable({ providedIn: 'root' })
export class PgModuleService {
  public resourceUrl = SERVER_API_URL + 'api/pg-modules';

  constructor(protected http: HttpClient) {}

  create(pgModule: IPgModule): Observable<EntityResponseType> {
    return this.http.post<IPgModule>(this.resourceUrl, pgModule, { observe: 'response' });
  }

  update(pgModule: IPgModule): Observable<EntityResponseType> {
    return this.http.put<IPgModule>(this.resourceUrl, pgModule, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgModule>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgModule[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
