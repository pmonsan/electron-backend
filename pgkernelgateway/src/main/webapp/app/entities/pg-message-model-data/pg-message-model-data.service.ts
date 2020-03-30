import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPgMessageModelData } from 'app/shared/model/pg-message-model-data.model';

type EntityResponseType = HttpResponse<IPgMessageModelData>;
type EntityArrayResponseType = HttpResponse<IPgMessageModelData[]>;

@Injectable({ providedIn: 'root' })
export class PgMessageModelDataService {
  public resourceUrl = SERVER_API_URL + 'api/pg-message-model-data';

  constructor(protected http: HttpClient) {}

  create(pgMessageModelData: IPgMessageModelData): Observable<EntityResponseType> {
    return this.http.post<IPgMessageModelData>(this.resourceUrl, pgMessageModelData, { observe: 'response' });
  }

  update(pgMessageModelData: IPgMessageModelData): Observable<EntityResponseType> {
    return this.http.put<IPgMessageModelData>(this.resourceUrl, pgMessageModelData, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPgMessageModelData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPgMessageModelData[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
