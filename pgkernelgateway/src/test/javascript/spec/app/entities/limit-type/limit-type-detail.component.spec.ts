/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { LimitTypeDetailComponent } from 'app/entities/limit-type/limit-type-detail.component';
import { LimitType } from 'app/shared/model/limit-type.model';

describe('Component Tests', () => {
  describe('LimitType Management Detail Component', () => {
    let comp: LimitTypeDetailComponent;
    let fixture: ComponentFixture<LimitTypeDetailComponent>;
    const route = ({ data: of({ limitType: new LimitType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [LimitTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LimitTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LimitTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.limitType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
