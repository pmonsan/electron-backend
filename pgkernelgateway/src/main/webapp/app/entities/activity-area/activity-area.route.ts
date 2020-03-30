import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ActivityArea } from 'app/shared/model/activity-area.model';
import { ActivityAreaService } from './activity-area.service';
import { ActivityAreaComponent } from './activity-area.component';
import { ActivityAreaDetailComponent } from './activity-area-detail.component';
import { ActivityAreaUpdateComponent } from './activity-area-update.component';
import { ActivityAreaDeletePopupComponent } from './activity-area-delete-dialog.component';
import { IActivityArea } from 'app/shared/model/activity-area.model';

@Injectable({ providedIn: 'root' })
export class ActivityAreaResolve implements Resolve<IActivityArea> {
  constructor(private service: ActivityAreaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IActivityArea> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ActivityArea>) => response.ok),
        map((activityArea: HttpResponse<ActivityArea>) => activityArea.body)
      );
    }
    return of(new ActivityArea());
  }
}

export const activityAreaRoute: Routes = [
  {
    path: '',
    component: ActivityAreaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.activityArea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ActivityAreaDetailComponent,
    resolve: {
      activityArea: ActivityAreaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.activityArea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ActivityAreaUpdateComponent,
    resolve: {
      activityArea: ActivityAreaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.activityArea.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ActivityAreaUpdateComponent,
    resolve: {
      activityArea: ActivityAreaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.activityArea.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const activityAreaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ActivityAreaDeletePopupComponent,
    resolve: {
      activityArea: ActivityAreaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.activityArea.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
