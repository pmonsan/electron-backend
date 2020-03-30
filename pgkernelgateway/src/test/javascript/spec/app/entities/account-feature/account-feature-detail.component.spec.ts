/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { AccountFeatureDetailComponent } from 'app/entities/account-feature/account-feature-detail.component';
import { AccountFeature } from 'app/shared/model/account-feature.model';

describe('Component Tests', () => {
  describe('AccountFeature Management Detail Component', () => {
    let comp: AccountFeatureDetailComponent;
    let fixture: ComponentFixture<AccountFeatureDetailComponent>;
    const route = ({ data: of({ accountFeature: new AccountFeature(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [AccountFeatureDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccountFeatureDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountFeatureDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountFeature).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
