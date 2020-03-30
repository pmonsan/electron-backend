import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ILimitMeasure } from 'app/shared/model/limit-measure.model';

type EntityResponseType = HttpResponse<ILimitMeasure>;
type EntityArrayResponseType = HttpResponse<ILimitMeasure[]>;

@Injectable({ providedIn: 'root' })
export class LimitMeasureService {
  public resourceUrl = SERVER_API_URL + 'api/limit-measures';

  constructor(protected http: HttpClient) {}

  create(limitMeasure: ILimitMeasure): Observable<EntityResponseType> {
    return this.http.post<ILimitMeasure>(this.resourceUrl, limitMeasure, { observe: 'response' });
  }

  update(limitMeasure: ILimitMeasure): Observable<EntityResponseType> {
    return this.http.put<ILimitMeasure>(this.resourceUrl, limitMeasure, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILimitMeasure>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILimitMeasure[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
