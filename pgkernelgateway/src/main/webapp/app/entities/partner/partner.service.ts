import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPartner } from 'app/shared/model/partner.model';

type EntityResponseType = HttpResponse<IPartner>;
type EntityArrayResponseType = HttpResponse<IPartner[]>;

@Injectable({ providedIn: 'root' })
export class PartnerService {
  public resourceUrl = SERVER_API_URL + 'api/partners';

  constructor(protected http: HttpClient) {}

  create(partner: IPartner): Observable<EntityResponseType> {
    return this.http.post<IPartner>(this.resourceUrl, partner, { observe: 'response' });
  }

  update(partner: IPartner): Observable<EntityResponseType> {
    return this.http.put<IPartner>(this.resourceUrl, partner, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartner>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartner[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
