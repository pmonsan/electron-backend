import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMeansofpayment } from 'app/shared/model/meansofpayment.model';

type EntityResponseType = HttpResponse<IMeansofpayment>;
type EntityArrayResponseType = HttpResponse<IMeansofpayment[]>;

@Injectable({ providedIn: 'root' })
export class MeansofpaymentService {
  public resourceUrl = SERVER_API_URL + 'api/meansofpayments';

  constructor(protected http: HttpClient) {}

  create(meansofpayment: IMeansofpayment): Observable<EntityResponseType> {
    return this.http.post<IMeansofpayment>(this.resourceUrl, meansofpayment, { observe: 'response' });
  }

  update(meansofpayment: IMeansofpayment): Observable<EntityResponseType> {
    return this.http.put<IMeansofpayment>(this.resourceUrl, meansofpayment, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMeansofpayment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMeansofpayment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
