import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPartnerType } from 'app/shared/model/partner-type.model';

type EntityResponseType = HttpResponse<IPartnerType>;
type EntityArrayResponseType = HttpResponse<IPartnerType[]>;

@Injectable({ providedIn: 'root' })
export class PartnerTypeService {
  public resourceUrl = SERVER_API_URL + 'api/partner-types';

  constructor(protected http: HttpClient) {}

  create(partnerType: IPartnerType): Observable<EntityResponseType> {
    return this.http.post<IPartnerType>(this.resourceUrl, partnerType, { observe: 'response' });
  }

  update(partnerType: IPartnerType): Observable<EntityResponseType> {
    return this.http.put<IPartnerType>(this.resourceUrl, partnerType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPartnerType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPartnerType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
