import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPartnerSecurity } from 'app/shared/model/partner-security.model';

type EntityResponseType = HttpResponse<IPartnerSecurity>;
type EntityArrayResponseType = HttpResponse<IPartnerSecurity[]>;

@Injectable({ providedIn: 'root' })
export class PartnerSecurityService {
  public resourceUrl = SERVER_API_URL + 'api/partner-securities';

  constructor(protected http: HttpClient) {}

  create(partnerSecurity: IPartnerSecurity): Observable<EntityResponseType> {
    return this.http.post<IPartnerSecurity>(this.resourceUrl, partnerSecurity, { observe: 'response' });
  }

  update(partnerSecurity: IPartnerSecurity): Observable<EntityResponseType> {
    return this.http.put<IPartnerSecurity>(this.resourceUrl, partnerSecurity, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartnerSecurity>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartnerSecurity[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
