import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  ActivityAreaComponent,
  ActivityAreaDetailComponent,
  ActivityAreaUpdateComponent,
  ActivityAreaDeletePopupComponent,
  ActivityAreaDeleteDialogComponent,
  activityAreaRoute,
  activityAreaPopupRoute
} from './';

const ENTITY_STATES = [...activityAreaRoute, ...activityAreaPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ActivityAreaComponent,
    ActivityAreaDetailComponent,
    ActivityAreaUpdateComponent,
    ActivityAreaDeleteDialogComponent,
    ActivityAreaDeletePopupComponent
  ],
  entryComponents: [
    ActivityAreaComponent,
    ActivityAreaUpdateComponent,
    ActivityAreaDeleteDialogComponent,
    ActivityAreaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayActivityAreaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
