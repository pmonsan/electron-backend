import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPricePlan } from 'app/shared/model/price-plan.model';

type EntityResponseType = HttpResponse<IPricePlan>;
type EntityArrayResponseType = HttpResponse<IPricePlan[]>;

@Injectable({ providedIn: 'root' })
export class PricePlanService {
  public resourceUrl = SERVER_API_URL + 'api/price-plans';

  constructor(protected http: HttpClient) {}

  create(pricePlan: IPricePlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pricePlan);
    return this.http
      .post<IPricePlan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(pricePlan: IPricePlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pricePlan);
    return this.http
      .put<IPricePlan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPricePlan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPricePlan[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(pricePlan: IPricePlan): IPricePlan {
    const copy: IPricePlan = Object.assign({}, pricePlan, {
      startDate: pricePlan.startDate != null && pricePlan.startDate.isValid() ? pricePlan.startDate.toJSON() : null,
      endDate: pricePlan.endDate != null && pricePlan.endDate.isValid() ? pricePlan.endDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate != null ? moment(res.body.startDate) : null;
      res.body.endDate = res.body.endDate != null ? moment(res.body.endDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((pricePlan: IPricePlan) => {
        pricePlan.startDate = pricePlan.startDate != null ? moment(pricePlan.startDate) : null;
        pricePlan.endDate = pricePlan.endDate != null ? moment(pricePlan.endDate) : null;
      });
    }
    return res;
  }
}
