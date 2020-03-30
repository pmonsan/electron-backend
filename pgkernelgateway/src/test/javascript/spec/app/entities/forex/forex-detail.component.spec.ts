/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { ForexDetailComponent } from 'app/entities/forex/forex-detail.component';
import { Forex } from 'app/shared/model/forex.model';

describe('Component Tests', () => {
  describe('Forex Management Detail Component', () => {
    let comp: ForexDetailComponent;
    let fixture: ComponentFixture<ForexDetailComponent>;
    const route = ({ data: of({ forex: new Forex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [ForexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ForexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ForexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.forex).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
