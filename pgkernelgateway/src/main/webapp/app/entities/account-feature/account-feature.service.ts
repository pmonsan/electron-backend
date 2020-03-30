import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccountFeature } from 'app/shared/model/account-feature.model';

type EntityResponseType = HttpResponse<IAccountFeature>;
type EntityArrayResponseType = HttpResponse<IAccountFeature[]>;

@Injectable({ providedIn: 'root' })
export class AccountFeatureService {
  public resourceUrl = SERVER_API_URL + 'api/account-features';

  constructor(protected http: HttpClient) {}

  create(accountFeature: IAccountFeature): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountFeature);
    return this.http
      .post<IAccountFeature>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(accountFeature: IAccountFeature): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountFeature);
    return this.http
      .put<IAccountFeature>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAccountFeature>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccountFeature[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(accountFeature: IAccountFeature): IAccountFeature {
    const copy: IAccountFeature = Object.assign({}, accountFeature, {
      activationDate:
        accountFeature.activationDate != null && accountFeature.activationDate.isValid() ? accountFeature.activationDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.activationDate = res.body.activationDate != null ? moment(res.body.activationDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((accountFeature: IAccountFeature) => {
        accountFeature.activationDate = accountFeature.activationDate != null ? moment(accountFeature.activationDate) : null;
      });
    }
    return res;
  }
}
