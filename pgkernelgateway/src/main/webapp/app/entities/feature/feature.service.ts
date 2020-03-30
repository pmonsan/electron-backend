import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFeature } from 'app/shared/model/feature.model';

type EntityResponseType = HttpResponse<IFeature>;
type EntityArrayResponseType = HttpResponse<IFeature[]>;

@Injectable({ providedIn: 'root' })
export class FeatureService {
  public resourceUrl = SERVER_API_URL + 'api/features';

  constructor(protected http: HttpClient) {}

  create(feature: IFeature): Observable<EntityResponseType> {
    return this.http.post<IFeature>(this.resourceUrl, feature, { observe: 'response' });
  }

  update(feature: IFeature): Observable<EntityResponseType> {
    return this.http.put<IFeature>(this.resourceUrl, feature, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFeature>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFeature[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
