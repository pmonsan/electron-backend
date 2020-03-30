import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgMessageModel } from 'app/shared/model/pg-message-model.model';

type EntityResponseType = HttpResponse<IPgMessageModel>;
type EntityArrayResponseType = HttpResponse<IPgMessageModel[]>;

@Injectable({ providedIn: 'root' })
export class PgMessageModelService {
  public resourceUrl = SERVER_API_URL + 'api/pg-message-models';

  constructor(protected http: HttpClient) {}

  create(pgMessageModel: IPgMessageModel): Observable<EntityResponseType> {
    return this.http.post<IPgMessageModel>(this.resourceUrl, pgMessageModel, { observe: 'response' });
  }

  update(pgMessageModel: IPgMessageModel): Observable<EntityResponseType> {
    return this.http.put<IPgMessageModel>(this.resourceUrl, pgMessageModel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgMessageModel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgMessageModel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
