import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IActivityArea } from 'app/shared/model/activity-area.model';

type EntityResponseType = HttpResponse<IActivityArea>;
type EntityArrayResponseType = HttpResponse<IActivityArea[]>;

@Injectable({ providedIn: 'root' })
export class ActivityAreaService {
  public resourceUrl = SERVER_API_URL + 'api/activity-areas';

  constructor(protected http: HttpClient) {}

  create(activityArea: IActivityArea): Observable<EntityResponseType> {
    return this.http.post<IActivityArea>(this.resourceUrl, activityArea, { observe: 'response' });
  }

  update(activityArea: IActivityArea): Observable<EntityResponseType> {
    return this.http.put<IActivityArea>(this.resourceUrl, activityArea, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IActivityArea>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IActivityArea[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
