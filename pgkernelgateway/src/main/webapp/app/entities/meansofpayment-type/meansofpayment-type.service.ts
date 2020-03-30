import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMeansofpaymentType } from 'app/shared/model/meansofpayment-type.model';

type EntityResponseType = HttpResponse<IMeansofpaymentType>;
type EntityArrayResponseType = HttpResponse<IMeansofpaymentType[]>;

@Injectable({ providedIn: 'root' })
export class MeansofpaymentTypeService {
  public resourceUrl = SERVER_API_URL + 'api/meansofpayment-types';

  constructor(protected http: HttpClient) {}

  create(meansofpaymentType: IMeansofpaymentType): Observable<EntityResponseType> {
    return this.http.post<IMeansofpaymentType>(this.resourceUrl, meansofpaymentType, { observe: 'response' });
  }

  update(meansofpaymentType: IMeansofpaymentType): Observable<EntityResponseType> {
    return this.http.put<IMeansofpaymentType>(this.resourceUrl, meansofpaymentType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMeansofpaymentType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMeansofpaymentType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
