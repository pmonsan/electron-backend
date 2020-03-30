import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  LimitMeasureComponent,
  LimitMeasureDetailComponent,
  LimitMeasureUpdateComponent,
  LimitMeasureDeletePopupComponent,
  LimitMeasureDeleteDialogComponent,
  limitMeasureRoute,
  limitMeasurePopupRoute
} from './';

const ENTITY_STATES = [...limitMeasureRoute, ...limitMeasurePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LimitMeasureComponent,
    LimitMeasureDetailComponent,
    LimitMeasureUpdateComponent,
    LimitMeasureDeleteDialogComponent,
    LimitMeasureDeletePopupComponent
  ],
  entryComponents: [
    LimitMeasureComponent,
    LimitMeasureUpdateComponent,
    LimitMeasureDeleteDialogComponent,
    LimitMeasureDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayLimitMeasureModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
